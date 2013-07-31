using ICSharpCode.AvalonEdit.Document;
using ICSharpCode.AvalonEdit.Editing;
using System;
using System.Collections.Generic;
using System.Linq;

namespace ICSharpCode.SharpDevelop.Gui
{
    public class BeginReadOnlySectionProvider : IReadOnlySectionProvider
    {
        public int EndOffset { get; set; }

        public bool CanInsert(int offset)
        {
            return offset >= EndOffset;
        }

        public IEnumerable<ISegment> GetDeletableSegments(ISegment segment)
        {
            if (segment.EndOffset < this.EndOffset)
                return Enumerable.Empty<ISegment>();

            return new[] {
				new TextSegment() {
					StartOffset = Math.Max(this.EndOffset, segment.Offset),
					EndOffset = segment.EndOffset
				}
			};
        }
    }
}
